package Blood_Mangement.Model.DAO;

import java.util.ArrayList;
import Blood_Mangement.Model.DTO.RequestDto;

public class RequestDao {
    private RequestDao() {}
    private static final RequestDao instance = new RequestDao();
    public static RequestDao getInstance() { return instance; }

    private ArrayList <RequestDto> rlist = new ArrayList<>();

    public boolean rListAdd(RequestDto requestDto){
        rlist.add(requestDto);
        return true;
    }
}
