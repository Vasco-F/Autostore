import React, {Component} from "react";
import {Container, Button, TextField, Box} from "@mui/material"
import ButtonAppBar from "./ButtonAppBar";
import {withRouter} from "react-router-dom";

import Typography from '@mui/material/Typography';

class VehicleAdd extends Component {
    
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

        await fetch("/vehicles",{
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item),
        })
        
        this.props.history.push("/vehicles/");            
    }

    render(){
        const {item} = this.state;            

        return <div>
            <ButtonAppBar/>
            <Container>
                <Box sx={{ width: '100%', maxWidth: 500 , p: 1}}>
                    <Typography variant="h4" component="div" gutterBottom>
                        Add Vehicle
                    </Typography>
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
                        label="Vehicle Image URL"
                        value={item.image || ""}
                        onChange={this.handleChange}
                    />
                </Box>
                <Box sx={{mt:2}}>
                    <Button variant="contained" type="submit" onClick={this.handleSubmit} color="success">Save</Button>{" "}
                    <Button variant="contained" href="/vehicles" color="error">Cancel</Button>
                </Box>
            </Container>
        </div>
    }
}
export default withRouter(VehicleAdd);