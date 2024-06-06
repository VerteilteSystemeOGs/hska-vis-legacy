package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.integration.user.ApiException;
import hska.iwi.eShopMaster.integration.user.RoleApiClientFactory;
import hska.iwi.eShopMaster.integration.user.UserApiClientFactory;
import hska.iwi.eShopMaster.integration.user.api.*;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3655805600703279195L;
    private String username;
    private String password1;
    private String password2;
    private String firstname;
    private String lastname;
    
    private RoleDTO role = null;

    private final UserApi userApi = new UserApi(UserApiClientFactory.getClient());
    private final RoleApi roleApi = new RoleApi(RoleApiClientFactory.getClient());

    @Override
    public String execute() throws Exception {
        
        // Return string:
        String result = "input";

   		this.role = roleApi.getRoleByLevel(new GetRoleByLevelRequestDTO().roleLevel(1)); // 1 -> regular User, 2-> Admin

        try {
            // save it to database
            userApi.createNewUser(
                new CreateNewUserRequestDTO()
                    .userName(this.username)
                    .firstName(this.firstname)
                    .lastName(this.lastname)
                    .password(this.password1)
            );

            // User has been saved successfully to databse:
            addActionMessage("user registered, please login");
            addActionError("user registered, please login");
            Map<String, Object> session = ActionContext.getContext().getSession();
            session.put("message", "user registered, please login");
            result = "success";
        } catch (ApiException e) {
            if (e.getCode() == 412) { // user name is in use
                addActionError(getText("error.username.alreadyInUse"));
            }
        }

        return result;

    }
    
	@Override
	public void validate() {
		if (getFirstname().length() == 0) {
			addActionError(getText("error.firstname.required"));
		}
		if (getLastname().length() == 0) {
			addActionError(getText("error.lastname.required"));
		}
		if (getUsername().length() == 0) {
			addActionError(getText("error.username.required"));
		}
		if (getPassword1().length() == 0) {
			addActionError(getText("error.password.required"));
		}
		if (getPassword2().length() == 0) {
			addActionError(getText("error.password.required"));
		}
		
		if (!getPassword1().equals(getPassword2())) {
			addActionError(getText("error.password.notEqual"));
		}
	}

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return (this.username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return (this.password1);
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }
    
    public String getPassword2() {
        return (this.password2);
    }

    public void setPassword2(String password) {
        this.password2 = password;
    }
    
    public RoleDTO getRole() {
        return (this.role);
    }
    
    public void setRole(RoleDTO role) {
        this.role = role;
    }

}
