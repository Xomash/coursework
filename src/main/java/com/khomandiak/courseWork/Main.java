package com.khomandiak.courseWork;

import java.io.*;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
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
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Main {

    private static String payaraPath = "static/configs/payara/domain.xml";
    private static String user = "volia";
    private static String password = "08DevTeam$$_l";
    private static String host = "10.10.16.35";
    private static int port = 22;

    private static String remoteFile = "/u01/volia/payara/glassfish/domains/domain1/config/domain.xml";

    public static void main(String[] args) throws Exception {
        /*
                        int exitCode = stopServer(server.getPathCommon(), session);
                if (exitCode < 0) {
                    disconnect(session);
                    throw new RuntimeException("Failed ..........");
                }
*/
        Repository repo = getRepo();
        PathFilter filter = PathFilter.create(payaraPath);
        RevCommit commit = getCommit(repo, filter);
        //Date date = new Date(commit.getCommitTime() * 1000L);

        RevTree tree = commit.getTree();
        TreeWalk treeWalk = getTreeWalk(repo, filter, tree);
        if (!treeWalk.next()) {
            System.out.println("Nothing found!");
            return;
        }
        //treeWalk.next();
        MessageDigest md = MessageDigest.getInstance("MD5");
        ByteArrayOutputStream out = getContent(treeWalk, repo);
        md.update(out.toByteArray());
        printCheckSum(md.digest());
        //System.out.println(out.toString());

        try {
            JSch jsch = new JSch();
            Session session = getSession(jsch);
            ChannelSftp sftpChannel = getChannelSftp(session);
            InputStream in = sftpChannel.get(remoteFile);
            FileInputStream fis = new FileInputStream("C:\\Users\\o.khomandiak\\IdeaProjects\\volia-billing\\static\\configs\\payara\\domain.xml");
            md = MessageDigest.getInstance("MD5");
            DigestInputStream dis = new DigestInputStream(fis, md);
            int b;
            do {
                b = dis.read();
            }
            while (b != -1);
            sftpChannel.exit();
            session.disconnect();
            printCheckSum(dis.getMessageDigest().digest());

        } catch (Exception e) {
            System.err.print(e);
        }
    }

    private static void printCheckSum(byte[] mdbytes) {
        StringBuffer sb = new StringBuffer("");
        for (byte mdbyte : mdbytes) {
            sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Digest(in hex format):: " + sb.toString());
    }

    private static ChannelSftp getChannelSftp(Session session) throws JSchException {
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();
        return sftpChannel;
    }

    private static Session getSession(JSch jsch) throws JSchException {
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications",
                "publickey,keyboard-interactive,password");
        session.connect();
        return session;
    }

    private static ByteArrayOutputStream getContent(TreeWalk treeWalk, Repository repo) throws IOException {
        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = repo.open(objectId);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        loader.copyTo(out);
        return out;
    }

    private static TreeWalk getTreeWalk(Repository repo, PathFilter filter, RevTree tree) throws IOException {
        TreeWalk treeWalk = new TreeWalk(repo);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(filter);
        return treeWalk;
    }

    private static Repository getRepo() throws IOException, GitAPIException {
        File gitWorkDir = new File("C:\\Users\\o.khomandiak\\IdeaProjects\\volia-billing");
        Git git = Git.open(gitWorkDir);
        git.checkout().setName(Constants.DEFAULT_REMOTE_NAME + "/uat").call();
        return git.getRepository();
    }

    static RevCommit getCommit(Repository repo, PathFilter filter) throws IOException {
        RevWalk revWalk = new RevWalk(repo);
        revWalk.markStart(revWalk.parseCommit(repo.resolve(Constants.HEAD)));
        revWalk.setTreeFilter(AndTreeFilter.create(filter, TreeFilter.ANY_DIFF));
        revWalk.sort(RevSort.COMMIT_TIME_DESC);
        revWalk.sort(RevSort.REVERSE, false);
        return revWalk.next();
    }

}
/*
            OutputStream outputStream = sftpChannel.put("/u01/volia/ok.xml");
            outputStream.write(out.toByteArray());
            outputStream.close();
*/


            /*
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
            br.close();
            */