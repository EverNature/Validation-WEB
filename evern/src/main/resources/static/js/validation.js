window.addEventListener("DOMContentLoaded", () => {
    console.log("DOM fully loaded and parsed")
    document.getElementById("correct").addEventListener("click", () =>{
        document.getElementById("animal").disabled = true;
        document.getElementById("submit").classList.remove("disabled");
        document.getElementById("submit").classList.add("enabled");
        document.getElementById("animal_text").disabled = true;
        document.getElementById("submit").disabled = false;
    })
    document.getElementById("incorrect").addEventListener("click", () =>{
        document.getElementById("animal").disabled = false;
        document.getElementById("submit").disabled = false;
        document.getElementById("submit").classList.remove("disabled");
        document.getElementById("submit").classList.add("enabled");

    })

    document.getElementById("animal").addEventListener("change", (element) => {
       if(element.target.value === "other"){
           document.getElementById("animal_text").disabled = false;
           document.getElementById("submit").classList.add("disabled");
           document.getElementById("submit").disabled = true;
           document.getElementById("submit").classList.remove("enabled");
       }
       else{
        document.getElementById("animal_text").disabled = true;
       }
    })

    document.getElementById("animal_text").addEventListener("change", (element) => {
        if(element.target.value !== ""){
            document.getElementById("submit").classList.remove("disabled");
            document.getElementById("submit").classList.add("enabled");
            document.getElementById("submit").disabled = false;
        }
        else {
            document.getElementById("submit").classList.add("disabled");
            document.getElementById("submit").classList.remove("enabled");
            document.getElementById("submit").disabled = true;
        }
    })
})