
$(function () {

    $(window).load(function () {

        var url = window.location.href
        
        var arr = url.split("/");
        var domain = arr[0] + "//" + arr[2] +"/"+ arr[3];
        
       

        if (window.location.hash) {

            var hash = window.location.hash.substring(1); //Puts hash in variable, and removes the # character
            window.location.href = domain + "/SSOffice365/?execute=VerifyToken&" + hash;

        } else {
            
            signIn();
        }
     

    });


});



var ADAL = new AuthenticationContext({
	instance: 'https://login.microsoftonline.com/',
	tenant: 'common', //COMMON OR YOUR TENANT ID

	/* produccion */
	clientId: '83f48914-35de-472c-a243-0f987a169e2a', //REPLACE WITH YOUR CLIENT ID PRODUCCION
	redirectUri: 'https://sorteoanahuac-sistema.azurewebsites.net/sorteo/ssoOffice365.html',//REPLACE WITH YOUR REDIRECT URL PRODUCCION
	/**/
	
	/* pruebas *
	clientId: 'b8e607c3-641d-4488-b75a-e6390cd3fed1',
	redirectUri: 'https://testsistema.sorteoanahuac.mx/sorteo/ssoOffice365.html',

	/**/
	
	/* local *
	clientId: 'b8e607c3-641d-4488-b75a-e6390cd3fed1',
	redirectUri: 'http://localhost:8080/sorteo/ssoOffice365.html',
	/**/

	callback: userSignedIn,
	popUp: false
});


function signIn() {
    ADAL.login();
}

function userSignedIn(err, token) {
    console.log('userSignedIn called');
    if (!err) {
        console.log("token: " + token);
        showWelcomeMessage();
    }
    else {
        console.error("error: " + err);
    }
}

function showWelcomeMessage() {
    var user = ADAL.getCachedUser();
    var divWelcome = document.getElementById('WelcomeMessage');
    divWelcome.innerHTML = "Welcome " + user.profile.name;
}

