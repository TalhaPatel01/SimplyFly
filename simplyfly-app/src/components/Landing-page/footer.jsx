function Footer() {
    return (
        <div className="container-fluid">
            <div className="row mt-5 text-center">

                <div className="col-md-4">
                    <div className="card border-0 shadow-sm rounded-3 p-3">
                        <h5 className="text-primary">Fast Booking</h5>
                        <p className="text-muted small">Book flights in seconds with easy steps</p>
                    </div>
                </div>

                <div className="col-md-4">
                    <div className="card border-0 shadow-sm rounded-3 p-3">
                        <h5 className="text-success">Secure Payment</h5>
                        <p className="text-muted small">Safe and reliable transactions</p>
                    </div>
                </div>

                <div className="col-md-4">
                    <div className="card border-0 shadow-sm rounded-3 p-3">
                        <h5 className="text-dark">Wide Network</h5>
                        <p className="text-muted small">Flights across multiple destinations</p>
                    </div>
                </div>

            </div>

            <div className="row mt-5">
                <div className="col-lg-12 text-center text-muted small">
                    © 2026 SimplyFly. All rights reserved.
                </div>
            </div>

        </div>
    )
}

export default Footer