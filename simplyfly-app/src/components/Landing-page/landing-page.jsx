import Footer from "./footer"
import LandingNavbar from "./landing-navbar"
import Search from "./search"

function LandingPage(){
    return (
        <div className="container-fluid">
            <div className="row">
                <LandingNavbar/>
            </div>
            <div className="row">
                <Search/>
            </div>
            <div className="row">
                <Footer/>
            </div>
        </div>
    )
}

export default LandingPage