package Blood_Mangement.Controller;

import Blood_Mangement.Model.DAO.BaseDao;
import Blood_Mangement.Model.DAO.RequestDao;
import Blood_Mangement.Model.DTO.RequestDto;

public class RequestController {
    private RequestController(){}
    private static final RequestController instance = new RequestController();
    public static RequestController getInstance(){return instance;}
    // 싱글톤
    private RequestDao rd = RequestDao.getInstance();

    // [1] 헌혈 요청글 작성
    public boolean rListAdd(RequestDto requestDto) {
        return rd.rListAdd(requestDto);
    }
}
