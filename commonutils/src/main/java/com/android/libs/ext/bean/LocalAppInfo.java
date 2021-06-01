package com.android.libs.ext.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.libs.ext.utils.PkgHelper;
import java.util.ArrayList;
/**
 * 本地应用信息类。
 * @author E
 */
public class LocalAppInfo {

	private String pkgName = null;
	private String labelName = null;
	private String version = null;
	private Drawable icon = null;

	public LocalAppInfo(String pkgName, String labelName, String version,
                        Drawable icon) {
		super();
		this.pkgName = pkgName;
		this.labelName = labelName;
		this.version = version;
		this.icon = icon;
	}

	/**
	 * 获取所有的非系统应用。
	 * @param context 上下文环境。
	 * @return 所有的非系统应用
	 */
	public static ArrayList<LocalAppInfo> getLocalInstallPackage(Context context){
		return PkgHelper.getLocalInstalPackage(context);
	}

	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "name:  " + labelName  + "    pkg:  " + pkgName + "    version:  " + version;
	}

}
