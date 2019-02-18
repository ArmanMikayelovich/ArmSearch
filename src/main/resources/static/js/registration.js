
function nameValidator(){
    var name = document.regForm.fullName.value;
    var errName = document.getElementById("errFName");
    if(firstName == "" || name == null){
        //alert(name);
        errName.innerHTML = "Provide First Name";
        errName.style.color = "red";
    }
}
function userNameValidator(){
    var userName = document.regForm.userName.value;
    var errUserName = document.getElementById("errUName");
    if(lastName=="" || userName==null){
        errUserName.innerHTML = "Provide Last Name";
        errUserName.style.color = "red";
    }
}

function emailValidator(){
    var email = document.regForm.email.value;
    var errEmail = document.getElementById("errEmail");
    if(email=="" || email==null){
        errEmail.innerHTML = "Provide email";
        errEmail.style.color = "red";
    }
}

function phoneValidator(){
    var phone = document.regForm.phone.value;
    var errPhone = document.getElementById("errPhone");
    if(phoneNumber=="" || phone==null){
        errPhone.innerHTML = "Provide Phone number";
        errPhone.style.color = "red";
    }
}

function passwordValidator(){
    var pwd = document.regForm.password.value;
    var confirmPwd = document.regForm.confirmPassword.value;
    var errpwd = document.getElementById("errPwd");
    var errConfirmPwd = document.getElementById("errConfirmPwd");
    if(password=="" || pwd==null){
        errpwd.innerHTML="Choose a password";
        errpwd.style.color = "red";
    }
    if(confirm_password == "" || confirmPwd == null){
        errConfirmPwd.innerHTML = "Re-type the password"
        errConfirmPwd.style.color = "red";
    }
    else if(password!=confirm_password){
        errConfirmPwd.innerHTML = "Password don't match";
    }
}

function validator(){
    firstNameValidator();
    lastNameValidator();
    emailValidator();
    phoneNumberValidator();
    passwordValidator();
}