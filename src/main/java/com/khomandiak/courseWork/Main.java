package com.khomandiak.courseWork;

import java.io.*;
import java.util.Date;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;


public class Main
{
    public static void main(String[] args) throws Exception
    {
        File gitWorkDir = new File("C:\\Users\\o.khomandiak\\IdeaProjects\\volia-billing-test");
        Git git = Git.open(gitWorkDir);
        git.checkout().setName(Constants.DEFAULT_REMOTE_NAME + "/master").call();
        Repository repo = git.getRepository();

        RevWalk revWalk = new RevWalk(repo);
        revWalk.markStart( revWalk.parseCommit( repo.resolve( Constants.HEAD ) ) );
        revWalk.setTreeFilter(AndTreeFilter.create(PathFilter.create( "core/resources/" ),  TreeFilter.ANY_DIFF));
        revWalk.sort( RevSort.COMMIT_TIME_DESC );
        revWalk.sort( RevSort.REVERSE, false );

        RevCommit commit = revWalk.next();
        Date date = new Date(commit.getCommitTime() * 1000L);

        RevTree tree = commit.getTree();
        TreeWalk treeWalk = new TreeWalk(repo);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create("core/resources/"));
        if (!treeWalk.next())
        {
            System.out.println("Nothing found!");
            return;
        }
        //treeWalk.next();

        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = repo.open(objectId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        loader.copyTo(out);

        //System.out.println("file1.txt:\n" + out.toString());

        String user = "volia";
        String password = "08DevTeam$$_l";
        String host = "10.10.16.35";
        int port=22;

        String remoteFile="/u01/volia/z.xml";

        try
        {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications",
                    "publickey,keyboard-interactive,password");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");

            OutputStream outputStream = sftpChannel.put("/u01/volia/ok.xml");
            outputStream.write(out.toByteArray());
            outputStream.close();


            InputStream in= null;
            in= sftpChannel.get(remoteFile);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
            br.close();

            sftpChannel.exit();
            session.disconnect();
        }
        catch(Exception e){System.err.print(e);}

    }
}
