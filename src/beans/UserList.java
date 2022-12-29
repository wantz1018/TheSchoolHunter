package beans;

import java.util.List;

public class UserList {
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    private List<User> userList;

}
