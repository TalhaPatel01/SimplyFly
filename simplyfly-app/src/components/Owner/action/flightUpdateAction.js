import axios from "axios"

export const UPDATE_FLIGHT_TIME = "UPDATE_FLIGHT_TIME"

export const flightUpdate = (routeFlightId, departureTime, arrivalTime) => {

    return async (dispatch) => {

        await axios.put(
            `http://localhost:8080/api/route/flight/update/${routeFlightId}`,
            {
                departureTime,
                arrivalTime
            },
            {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
        )

        dispatch({
            type: UPDATE_FLIGHT_TIME,
            payload: {
                routeFlightId,
                departureTime,
                arrivalTime
            }
        })
    }
}