function mathRandom() {
    return new Promise((resolve, reject) => {
        // réussir une fois sur deux
        setTimeout(() => {
            if (Math.random > 0.5) {
                resolve(result);
            }
            else {
                reject(error);
            }
        }, 3000);
        
    });
}

function loadScript(src) {
    return new Promise((resolve, reject) => {
        let script = document.createElement("script");
        script.src = src;
        if (src == '2.js'){
            setTimeout(() => {
                document.head.append(script);
            }, 4000);
        }
        else{
            document.head.append(script);
        }
        script.onload = () => resolve("Fichier" + src + " bien chargé");
        script.onerror = () => reject(new Error("Echec de chargement de " + src));
    });
}

async function loadAllScripts(){
    const promesse1 = await loadScript( '1.js'). then( function (value) {
        console.log(value);
    });
    const promesse2 = await loadScript("2.js").then(function (value){
        console.log(value);
    });
    const promesse3 = await loadScript('3.js') .then (function(value) {
        console.log(value);
    });
}

loadAllScripts();