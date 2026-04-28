import { createStore, applyMiddleware, combineReducers } from 'redux'
import { thunk } from 'redux-thunk'

import flightReducer from './components/Owner/reducer/flightUpdateReducer'

const reducers = combineReducers({
    flightReducer: flightReducer
})

export const store = createStore(reducers,
    applyMiddleware(thunk))