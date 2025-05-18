import dingo_image from '../assets/Dingo_Infobox.webp'

function Characters(){
    let characters = [
        { name: "Dingo", image: dingo_image},
    ]

    function createChatBox(character, e){
        console.log("Création chat box pour : " + character.name);

    }
    return (
        <section className="w-[50%] m-8 p-4">
                {characters.map((character, index) => {
                    return (
                        <div className="flex flex-col items-center w-[20%] 	bg-[#181F24] p-3 rounded-lg" key={index}>
                            <p className="font-bold">{character.name}</p>
                            <img src={character.image} width="100%" height="100%" className="m-2"/>
                            <button className="bg-white text-black p-1.5 font-semibold
                            hover:bg-amber-50 hover:shadow-lg rounded-lg active:scale-95" onClick={(e) => createChatBox(character, e)}>Talk with him</button>
                        </div>            

                    )

                })}
        </section>
    )
}

export default Characters