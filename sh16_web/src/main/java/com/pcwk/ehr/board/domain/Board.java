package com.pcwk.ehr.board.domain;

import com.pcwk.ehr.cmn.DTO;

public class Board extends DTO {
	private int    seq      ;//순번
	private String div      ;//구분
	private String title    ;//제목
	private String contents ;//내용
	private int    readCnt  ;//조회수
	private String regId    ;//등록자
	private String regDt    ;//등록일
	private String modId    ;//수정자
	private String modDt    ;//수정일
	
	public Board() {}

	public Board(int seq, String div, String title, String contents, int readCnt, String regId, String regDt,
			String modId, String modDt) {
		super();
		this.seq = seq;
		this.div = div;
		this.title = title;
		this.contents = contents;
		this.readCnt = readCnt;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	@Override
	public String toString() {
		return "Board [seq=" + seq + ", div=" + div + ", title=" + title + ", contents=" + contents + ", readCnt="
				+ readCnt + ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt=" + modDt
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
