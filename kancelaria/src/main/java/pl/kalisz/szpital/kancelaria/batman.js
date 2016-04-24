alert('Script loaded');
var i = setInterval(batmanFunction, 300);

function batmanFunction()
{
    var element = document.getElementById("user");
    if(element != null)
    {
        element.addEventListener("mouseover", batmanScript);
    	$( "#user" ).css( "background-color", "#BADA55" );
        clearInterval(i);
    }
}
function batmanScript()
{
    alert('I am batman');
}