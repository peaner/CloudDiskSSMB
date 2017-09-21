package cn.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class QueryMySharedFileInfo extends ShareInfo{
	
	private static final long serialVersionUID = -8780937114148421489L;
	private String fileName;
	private String fileSize;
	private String fileType;
	private String shareObjectName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the shareObjectName
	 */
	public String getShareObjectName() {
		return shareObjectName;
	}
	/**
	 * @param shareObjectName the shareObjectName to set
	 */
	public void setShareObjectName(String shareObjectName) {
		this.shareObjectName = shareObjectName;
	}
	
}
