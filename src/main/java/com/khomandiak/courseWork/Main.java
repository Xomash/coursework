package com.khomandiak.courseWork;

import java.io.*;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.jce.MD5;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.codec.digest.DigestUtils;
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

    private static String payaraPath = "static/configs/payara/volia-core-log4j.xml";
    private static String user = "volia";
    private static String password = "08DevTeam$$_l";
    private static String host = "10.10.16.35";
    private static int port = 22;

    private static String remoteFile = "/u01/volia/payara/glassfish/domains/domain1/config/volia-core-log4j.xml";

    public static void main(String[] args) throws Exception {
        /*
                        int exitCode = stopServer(server.getPathCommon(), session);
                if (exitCode < 0) {
                    disconnect(session);
                    throw new RuntimeException("Failed ..........");
                }
*/
/*
        Repository repo = getRepo();
        TreeWalk treeWalk = getTreeWalk(repo, 0);
        DigestOutputStream dos = getContent(treeWalk,repo);
        printCheckSum(dos.getMessageDigest().digest());
        int k = (int)(System.currentTimeMillis() / 1000);
        System.out.println(k);
        try {
            JSch jsch = new JSch();
            Session session = getSession(jsch);
            ChannelSftp sftpChannel = getChannelSftp(session);
            InputStream in = sftpChannel.get(remoteFile);
            System.out.println(sftpChannel.stat(remoteFile).getMTime());
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            DigestInputStream dis = new DigestInputStream(in, md);
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
    */
    Git git = getGit();
    FileOutputStream fos = new FileOutputStream("src/config/test.txt");
    fos.write("Hi".getBytes());
    git.add().addFilepattern("src/config/test.txt").call();
    RevCommit commit = git.commit().setMessage("Ok").call();
    git.checkout().setName(Constants.DEFAULT_REMOTE_NAME + "/volia_config").call();
    //git.push().call();
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

    private static DigestOutputStream getContent(TreeWalk treeWalk, Repository repo) throws IOException, NoSuchAlgorithmException {
        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = repo.open(objectId);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        DigestOutputStream dos = new DigestOutputStream(out, md);
        //System.out.println(DigestUtils.md5Hex(out.toByteArray()));
        byte [] bytes = loader.getBytes();
        for (byte aByte : bytes) {
            dos.write(aByte);
        }
        return dos;
    }

    private static TreeWalk getTreeWalk(Repository repo, Integer commitNumber) throws IOException, GitAPIException {
        PathFilter filter = PathFilter.create(payaraPath);
        RevCommit commit = getCommit(repo, filter, commitNumber);
        RevTree tree = commit.getTree();
        TreeWalk treeWalk = new TreeWalk(repo);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(filter);
        if (!treeWalk.next()) {
            System.out.println("Nothing found!");
            return null;
        }
        System.out.println(getCommitDate(commit) + " " + commit.getFullMessage());
        return treeWalk;
    }

    private static String getCommitDate(RevCommit commit) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(commit.getCommitTime() * 1000L));
    }

    private static Repository getRepo() throws IOException, GitAPIException {
        Git git = getGit();
        return git.getRepository();
    }

    private static Git getGit() throws IOException, GitAPIException {
        File gitWorkDir = new File("/");
        Git git = Git.open(gitWorkDir);
        git.checkout().setName(Constants.DEFAULT_REMOTE_NAME + "/volia_config").call();
        return git;
    }

    static RevCommit getCommit(Repository repo, PathFilter filter, Integer commitNumber) throws IOException {
        RevWalk revWalk = new RevWalk(repo);
        revWalk.markStart(revWalk.parseCommit(repo.resolve(Constants.HEAD+"~"+commitNumber)));
        revWalk.setTreeFilter(AndTreeFilter.create(filter, TreeFilter.ANY_DIFF));
        revWalk.sort(RevSort.COMMIT_TIME_DESC);
        revWalk.sort(RevSort.REVERSE, false);
        return revWalk.next();
    }

    private static ChannelExec execute(Session session, String command) throws IOException, JSchException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream inStr = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        return channelExec;
    }

    private static void hello(){
        System.out.println("hello");
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