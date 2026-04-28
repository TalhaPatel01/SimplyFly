import Search from "../Landing-page/search"
import UserNavbar from "./user-navbar"

function UserDashboard(){
    return(
        <div className="container-fluid">
            <div className="row">
                <UserNavbar/>
            </div>
            <div className="row mt-4">
                <Search/>
            </div>
        </div>
    )
}

export default UserDashboard