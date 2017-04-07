package coder.dasu.meizi.mode.entity;

/**
 * Created by suxq on 2017/4/7.
 *
 * fir.im API接口返回版本查询信息
 */

public class VersionResEntity {

    private String name;            //应用名称
    private String version;         //版本
    private String changelog;       //更新日志
    private String versionShort;    //版本编号(兼容旧版字段)
    private String build;           //编译号
    private String installUrl;      //安装地址（兼容旧版字段）
    private String install_url;     //安装地址(新增字段)
    private String update_url;      //更新地址(新增字段)
    private Object binary;          //更新文件的对象，仅有大小字段fsize

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public Object getBinary() {
        return binary;
    }

    public void setBinary(Object binary) {
        this.binary = binary;
    }

    @Override
    public String toString() {
        return "VersionResEntity{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", changelog='" + changelog + '\'' +
                ", versionShort='" + versionShort + '\'' +
                ", build='" + build + '\'' +
                ", installUrl='" + installUrl + '\'' +
                ", install_url='" + install_url + '\'' +
                ", update_url='" + update_url + '\'' +
                ", binary=" + binary +
                '}';
    }

    /**
     *
     {
        "name": "fir.im",
        "version": "1.0",
        "changelog": "更新日志",
        "versionShort": "1.0.5",
        "build": "6",
        "installUrl": "http://download.fir.im/v2/app/install/xxxxxxxxxxxxxxxxxxxx?download_token=xxxxxxxxxxxxxxxxxxxxxxxxxxxx",
        "install_url": "http://download.fir.im/v2/app/install/xxxxxxxxxxxxxxxx?download_token=xxxxxxxxxxxxxxxxxxxxxxxxxxxx",   # 新增字段
        "update_url": "http://fir.im/fir",  # 新增字段
        "binary": {
        "fsize": 6446245
        }
     }
     */

}
