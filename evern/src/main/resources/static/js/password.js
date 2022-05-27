window.addEventListener("DOMContentLoaded", () => {
    console.log("DOM fully loaded and parsed");
    document.getElementById("customCheckbox").addEventListener("click", changeVisivility, false)
})

function changeVisivility() {
    console.log("changeVisivility");
    var x = document.getElementById("passwordField");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}