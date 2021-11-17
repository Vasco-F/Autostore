import React, {Component} from "react";
import {Container, Button, TextField, Box, Typography} from "@mui/material"
import ButtonAppBar from "./ButtonAppBar";
import {withRouter} from "react-router-dom";

import "./ImageCenter.css";

class VehicleView extends Component {
    
    emptyItem = {
        manufacturer: "",
        model: "",
        year: 0,
        consumption: 0,
        image: ""
    };

    constructor(props){
        super(props);
        this.state = {
            item: this.emptyItem
        };
    }

    async componentDidMount(){
        if(this.props.match.params.id){
            const vehicle = await (await fetch(`/vehicles/${this.props.match.params.id}`)).json();
            this.setState({
                item: vehicle
            })
        }
    }

    render(){
        const {item} = this.state;           

        let vehicle_image;
        if(item.image && item.image !== "")
            vehicle_image = <img src={item.image} alt="Vehicle" className="vehicle-img"/>;

        return <div>
            <ButtonAppBar/>
            <Container>
                <Box sx={{ width: '100%', maxWidth: 500 , p: 1}}>
                    <Typography variant="h4" component="div" gutterBottom>
                        Vehicle Roadtrip Calculator
                    </Typography>
                </Box>
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
                        '& > :not(style)': { mb: 1, width: '100%' },
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="manufacturer"
                        label="Vehicle Manufacturer"
                        value={item.manufacturer || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="model"
                        label="Vehicle Model"
                        value={item.model || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="year"
                        label="Year"
                        value={item.year || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        disabled
                        id="outlined-read-only-input"
                        name="consumption"
                        label="Vehicle Consumption"
                        value={item.consumption || ""}
                        onChange={this.handleChange}
                    />
                </Box>
                <Box sx={{mt: 2}}>
                    <Button variant="contained" href={"/vehicles/" + item.vehicleId + "/update"}>Edit Vehicle Info</Button>{" "}
                    <Button variant="contained" href={"/vehicles/" + item.vehicleId + "/roadtrip-cost"}>Roadtrip Cost Calculator</Button>
                </Box>
            </Container>
        </div>
    }
}
export default withRouter(VehicleView);