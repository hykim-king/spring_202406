package com.pcwk.ehr.cmn;

/**
 * 모든 Value Object는 DTO를 상속 받아야 한다.
 * @author acorn
 *
 */
public class DTO {

	private int no;//글번호
	private int totalCnt;//총 글수
	
	public DTO() { }

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	@Override
	public String toString() {
		return "DTO [no=" + no + ", totalCnt=" + totalCnt + "]";
	}


	
	
}
