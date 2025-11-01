package dto;

public class PagingDTO {

	private int currentPage;
	// 현재 페이지 번호
	private int totalPage;
	// 전체 페이지 수
	private int startPage;
	// 현재 블록의 시작 페이지
	private int endPage;
	// 현재 블록의 마지막 페이지
	private int pagingBlockSize;
	// 페이징 바에 보여줄 페이지 수 (예: 20개씩)
    private int pageSize;      
    // 한 페이지당 표시할 항목 수
    private int totalCount;    
    // 전체 데이터 수

    // 생성자
    public PagingDTO(int currentPage, int totalPage, int startPage, int endPage, int pagingBlockSize) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.startPage = startPage;
        this.endPage = endPage;
        this.pagingBlockSize = pagingBlockSize;
    }
	// 현재 페이지 번호, 전체 페이지 수, 현재 블록의 시작 페이지, 현재 블록의 마지막 페이지, 페이징 바에 보여줄 페이지 수

    // getter & setter 메서드
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public int getpagingBlockSize() {
		return pagingBlockSize;
	}
	public void setpagingBlockSize(int pagingBlockSize) {
		this.pagingBlockSize = pagingBlockSize;
	}
}
