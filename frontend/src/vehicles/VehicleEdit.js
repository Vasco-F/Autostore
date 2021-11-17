import React, {Component} from "react";
import {Container, Button, TextField, Box} from "@mui/material"
import ButtonAppBar from "./ButtonAppBar";
import {withRouter} from "react-router-dom";

import "./ImageCenter.css";

class VehicleEdit extends Component {
    
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
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount(){
        const vehicle = await (await fetch(`/vehicles/${this.props.match.params.id}`)).json();
    
        this.setState({
            item: vehicle
        })
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
        const {item} = this.state;

        await fetch("/vehicles/" + item.vehicleId,{
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item),
        })
        
        this.props.history.push("/vehicles/" + item.vehicleId);
            
    }  

    render(){
        const {item} = this.state;

        let vehicle_image;
        if(item.image && item.image !== "")
            vehicle_image = <img src={item.image} alt="Vehicle" className="vehicle-img"/>;

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
                        '& > :not(style)': { mb: 1, width: '100%' },
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <TextField
                        required
                        id="outlined-required"
                        name="manufacturer"
                        label="Vehicle Manufacturer"
                        value={item.manufacturer || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        name="model"
                        label="Vehicle Model"
                        value={item.model || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        name="year"
                        label="Year"
                        value={item.year || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        name="consumption"
                        label="Vehicle Consumption"
                        value={item.consumption || ""}
                        onChange={this.handleChange}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        name="image"
                        label="Vehicle Image"
                        value={item.image || ""}
                        onChange={this.handleChange}
                    />
                </Box>
                <Box>
                    <Button variant="contained" type="submit" onClick={this.handleSubmit} color="success">Save</Button>{" "}
                    <Button variant="contained" href="/vehicles" color="error">Cancel</Button>
                </Box>
            </Container>
        </div>
    }
}
export default withRouter(VehicleEdit);