window.addEventListener("DOMContentLoaded", () => {
    console.log("DOM fully loaded and parsed");
    document.getElementById("email").addEventListener("change", (event) => {
        if (event.target.value.length > 0) {
            document.getElementById("send").classList.add("enabled");
            document.getElementById("send").classList.remove("disabled");
        }
        else {
            document.getElementById("send").classList.remove("enabled");
            document.getElementById("send").classList.add("disabled");
        }
    });
})