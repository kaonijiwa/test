package com.example.myproject02.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class GitUtill {
    //定义本地git路径
    public static final String LOCALPATH = "D:/git_home/demo/";
    //.git文件路径
    public static final String LOCALGITFILE = LOCALPATH + ".git";
    //远程仓库地址
    public static final String REMOTEREPOURI = "https://github.com/pengzhongghost/google.git";
    //操作git的用户名
    public static final String USER = "189968924@qq.com";
    //密码
    public static final String PASSWORD = "3852ghost";
    //git远程仓库服务器ip
    public static final String HOST = "github.com";

    //建立与远程仓库的联系，仅需要执行一次
    public static String setupRepo() {
        String msg = "";
        try {
            Git git = Git.cloneRepository()
                    .setURI(REMOTEREPOURI)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USER, PASSWORD))
                    .setBranch("master")
                    .setDirectory(new File(LOCALPATH))
                    .call();
            msg = "git init success！";
        } catch (Exception e) {
            msg = "git已经初始化！";
        }
        return msg;
    }

    //pull拉取远程仓库文件
    public static boolean pullBranchToLocal() {
        boolean resultFlag = false;
        //git仓库地址
        Git git;
        try {
            git = new Git(new FileRepository(LOCALGITFILE));
            git.pull().setRemoteBranchName("master")
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USER, PASSWORD))
                    .call();
            resultFlag = true;
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
        return resultFlag;
    }

    public static void main(String[] args) {
        //https://github.com/pengzhongghost/google/archive/master.zip
        setupRepo();
        pullBranchToLocal();
    }
}
