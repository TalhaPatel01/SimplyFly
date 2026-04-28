import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Login from './components/auth/login.jsx'
import SignUp from "./components/auth/sign-up.jsx"
import UserDashboard from './components/User/user-dashboard.jsx'
import OwnerDashboard from './components/Owner/owner-dashboard.jsx'
import AdminDashboard from './components/Admin/admin-dashboard.jsx'
import MyTrips from './components/User/my-trips.jsx'
import SearchResults from './components/User/search-results.jsx'
import AddPassengers from './components/User/add-passengers.jsx'
import SelectSeats from './components/User/select-seats.jsx'
import ConfirmBooking from './components/User/confirm-booking.jsx'
import Payment from './components/User/payment.jsx'
import BookingSuccess from './components/User/booking-success.jsx'
import PaymentById from './components/User/payment-by-id.jsx'
import PaymentSuccess from './components/User/payment-success.jsx'
import TicketPage from './components/User/ticket-page.jsx'
import Flights from './components/Owner/flights.jsx'
import AddFlight from './components/Owner/add-flight.jsx'
import FlightDetails from './components/Owner/flight-details.jsx'
import FlightPassengers from './components/Owner/flight-passengers.jsx'
import SeatMap from './components/Owner/seat-map.jsx'
import Bookings from './components/Owner/bookings.jsx'
import BookingDetails from './components/Owner/booking-details.jsx'
import UserList from './components/Admin/user-list.jsx'
import OwnerList from './components/Admin/owner-list.jsx'
import RouteList from './components/Admin/route-list.jsx'
import AirportList from './components/Admin/airport-list.jsx'
import EditFlight from './components/Owner/edit-flight.jsx'
import { Provider } from 'react-redux'
import {store} from './store.js'
import UploadDocument from './components/User/upload-document.jsx'

const routes = createBrowserRouter([
    {
        path: "/",
        element: <App />
    },
    {
        path: "/login",
        element: <Login />
    },
    {
        path: "/sign-up",
        element: <SignUp />
    },
    {
        path: "/user-dashboard",
        element: <UserDashboard />
    },
    {
        path: "/owner-dashboard",
        element: <OwnerDashboard />
    },
    {
        path: "/admin-dashboard",
        element: <AdminDashboard />
    },
    {
        path: "/my-trips",
        element: <MyTrips />
    },
    {
        path: "/search-results",
        element: <SearchResults />
    },
    {
        path: "/passengers",
        element: <AddPassengers />
    },
    {
        path: "/select-seats",
        element: <SelectSeats />
    },
    {
        path: "/confirm-booking",
        element: <ConfirmBooking />
    },
    {
        path: "/upload-document",
        element: <UploadDocument/>
    },
    {
        path: "/payment",
        element: <Payment />
    },
    {
        path: "/booking-success",
        element: <BookingSuccess />
    },
    {
        path: "/payment/:bookingId",
        element: <PaymentById />
    },
    {
        path: "/payment-success",
        element: <PaymentSuccess />
    },
    {
        path: "/ticket/:bookingId",
        element: <TicketPage />
    },
    {
        path: "/owner/flights",
        element: <Flights />
    },
    {
        path: "/add-flight",
        element: <AddFlight />
    },
    {
        path: "/view/flight/details/:routeFlightId",
        element: <FlightDetails />
    },
    {
        path: "/flight/passenger/:routeFlightId",
        element: <FlightPassengers />
    },
    {
        path: "/flight/seat/map/:routeFlightId",
        element: <SeatMap />
    },
    {
        path: "/owner/bookings",
        element: <Bookings />
    },
    {
        path: "/owner/bookings/:bookingId",
        element: <BookingDetails />
    },
    {
        path: "/list/users",
        element: <UserList />
    },
    {
        path: "/list/owners",
        element: <OwnerList />
    },
    {
        path: "/list/routes",
        element: <RouteList />
    },
    {
        path: "/list/airports",
        element: <AirportList />
    },
    {
        path: "/edit-time/:routeFlightId",
        element: <EditFlight />
    }
])

createRoot(document.getElementById('root')).render(
    <Provider store={store}>
        <RouterProvider router={routes}>
            <App />
        </RouterProvider>
    </Provider>
)