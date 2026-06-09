const buttons = document.querySelectorAll("button");
const span = document.querySelector('span');

buttons.forEach(element => {
    element.addEventListener('click',(e)=>{
        console.log(e.target.textContent);

        let message = e.target.textContent;

        if(message.includes("e")){
            clearText(span);
            return;
        }
        
        if(span.textContent.includes(message)){
            span.textContent += message;
            console.log(e.target.textContent);

        }
        else{
            span.textContent = message;

        }
    });
});

function clearText(o){
    o.textContent = "";
}