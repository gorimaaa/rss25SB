import './App.css'
import Navbar from './components/Navbar'
import Characters from './components/Characters'
import Chatbox from './components/ChatBox'
function App() {

  return (
    <div >
      <Navbar/>
      <div className="flex flex-col items-center w-full">
        <Characters/>
      </div>
      <Chatbox/>
    </div>
 
  )
}

export default App
