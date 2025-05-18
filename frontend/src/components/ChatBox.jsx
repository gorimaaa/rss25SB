import { useState } from 'react';
import dingo_image from '../assets/Dingo_Infobox.webp'

function Chatbox(){
    const [messages, setMessages] = useState([]);
    function sendMessage(e){
        if(e.key == 'Enter'){
            console.log(e.target.value);
            setMessages([...messages, e.target.value]);
            e.target.value='';
        }
    }
   return (
    <section className='fixed bottom-0 right-5 flex flex-col  w-[20%] h-[50%] rounded-lg bg-neutral-500'>
        <div className='flex flex-row items-center justify-between p-2 h-auto'>
            <img src={dingo_image} alt="Dingo" className='w-[15%]'/>
            <p className='font-semibold'>Dingo</p>
        </div>
        
        <div id='textArea' className='m-2 flex-1 overflow-y-auto'>
            {messages.map((message, index) => {
                return (
                    <p key={index}>{message}</p>
                )
            }) }
        </div>
        <div className='m-2'>
            <input type="text" placeholder='Ecrivez votre message ...' onKeyDown={(e) => sendMessage(e)}/>
        </div>

    </section>
   )
}

export default Chatbox