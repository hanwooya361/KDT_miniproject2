package Blood_Mangement;

import Blood_Mangement.Model.DTO.MemberDto;
import Blood_Mangement.View.MainView;


public class AppStart {
    public static void main(String[] args) {
        MemberDto loginMember = null;
        MainView.getInstance().run(loginMember);
    }
}

