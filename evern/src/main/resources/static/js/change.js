window.addEventListener("DOMContentLoaded", () => {
    console.log("DOM fully loaded and parsed");
    const password_1 = document.getElementById("password_1");
    const password_2 = document.getElementById("password_2");

    password_1.addEventListener("change", (event) => {
        if (event.target.value.length > 0) {
            if (password_1.value === password_2.value) {
                document.getElementById("send").classList.add("enabled");
                document.getElementById("send").classList.remove("disabled");
                document.getElementById("send").disabled = false;
            } else {
                document.getElementById("send").disabled = true;
            }
        }
        else {
            document.getElementById("send").classList.remove("enabled");
            document.getElementById("send").classList.add("disabled");
        }
    });

    password_2.addEventListener("change", (event) => {
        if (event.target.value.length > 0) {
            if (password_1.value === password_2.value) {
                document.getElementById("send").classList.add("enabled");
                document.getElementById("send").classList.remove("disabled");
                document.getElementById("send").disabled = false;
            } else {
                document.getElementById("send").disabled = true;
            }
        }
        else {
            document.getElementById("send").classList.remove("enabled");
            document.getElementById("send").classList.add("disabled");
        }
    });
})