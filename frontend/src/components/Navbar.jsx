function Navbar() {

  return (
    <header>
      <nav className="bg-[#2C373D] shadow">
          <ul className="flex items-center justify-between p-4 text-[15px] font-semibold">
            <li>
              <a href="#" className="hover:opacity-90 hover:rounded-full p-2 ml-4" >Home</a>
            </li>
            <div className="flex space-x-12">
              <li>
                <a href="#" className="hover:opacity-90 hover:rounded-full p-2">About</a>
              </li>
              <li>
                <a href="#" className="hover:opacity-90 hover:rounded-full p-2">Services</a>
              </li>
              <li>
                <a href="#" className="hover:opacity-90 hover:rounded-full p-2 mr-4">Contact</a>
              </li>
            </div>
          </ul>
      </nav>
    </header> 
  )
}

export default Navbar
