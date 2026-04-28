import { UPDATE_FLIGHT_TIME } from "../action/flightUpdateAction"

const initialState = {
    flights: []
}

const flightReducer = (state = initialState, action) => {

    switch (action.type) {

        case UPDATE_FLIGHT_TIME:
            return {
                ...state,
                flights: state.flights.map((f) =>
                    f.routeFlightId === action.payload.id
                        ? {
                            ...f,
                            departureTime: action.payload.departureTime,
                            arrivalTime: action.payload.arrivalTime
                        }
                        : f
                )
            }

        default:
            return state
    }
}

export default flightReducer