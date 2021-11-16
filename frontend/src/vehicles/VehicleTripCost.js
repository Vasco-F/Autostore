import React, {Component} from "react";
import {Container, Button, TextField, Box} from "@mui/material"
import ButtonAppBar from "./ButtonAppBar";
import {withRouter} from "react-router-dom";

import "./ImageCenter.css";

class VehicleTripCost extends Component {
    
    emptyVehicle = {
        manufacturer: "",
        model: "",
        year: 0,
        consumption: 0,
        image: ""
    };

    emptyItem = {
        vehicle: this.emptyVehicle,
        distance: 0,
        fuelPrice: 0,
        cost: 0
    };

    constructor(props){
        super(props);
        this.state = {
            item: this.emptyItem,
            calculated: false
        };
    }

    async componentDidMount(){
        if(!this.state.calculated){
            const vehicle = await (await fetch(`/vehicles/${this.props.match.params.id}`)).json();

            this.emptyItem = {
                vehicle: vehicle,
                distance: 0,
                fuelPrice: 0,
                cost: 0
            };

            this.setState({
                item: this.emptyItem
            })
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event){
        event.preventDefault();

        const result = await (await fetch(`/vehicles/${this.props.match.params.id}/roadtrip-calculator`)).json();
        this.setState({
            item: result
        })
    }  

    render(){
        const {item} = this.state;  
        
        let vehicle_image;
        if(item.vehicle.image && item.vehicle.image !== "")
            vehicle_image = <img src={item.vehicle.image} alt="Vehicle" className="vehicle-img"/>;

        return <div>
            <ButtonAppBar/>
            <Container>
                <h2>Vehicle Roadtrip Calculator</h2>
                <Box
                    sx={{ 
                        mb: 2,
                        mx: "auto"
                    }}
                >
                    {vehicle_image}
                </Box>
                <Box
                    component="form"
                    sx={{
                        '& > :not(style)': { m: 1, width: '100%' },
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="manufacturer"
                        label="Vehicle Manufacturer"
                        value={item.vehicle.manufacturer || ""}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="model"
                        label="Vehicle Model"
                        value={item.vehicle.model || ""}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="year"
                        label="Year"
                        value={item.vehicle.year || ""}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="consumption"
                        label="Vehicle Consumption"
                        value={item.vehicle.consumption || ""}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        name="distance"
                        label="Distance"
                        onChange={this.handleChange}
                        value={item.distance || ""}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        name="fuelPrice"
                        label="Fuel Price"
                        onChange={this.handleChange}
                        value={item.fuelPrice || ""}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        label="Roadtrip Cost"
                        value={item.cost || ""}
                    />
                </Box>
                <Box>
                    <Button variant="contained" type="submit" onClick={this.handleSubmit}>Calculate</Button>{" "}
                    <Button variant="contained" href="/vehicles">Vehicles List</Button>
                </Box>
            </Container>
        </div>
    }
}
export default withRouter(VehicleTripCost);