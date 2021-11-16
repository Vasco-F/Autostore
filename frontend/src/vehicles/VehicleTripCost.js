import React, {Component} from "react";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AppNavbar from "./AppNavbar";
import {Link, withRouter} from "react-router-dom";

import "./VehicleView.css";

class VehicleTripCost extends Component {
    
    emptyVehicle = {
        manufacturer: "",
        model: "",
        year: 0,
        consumption: 0,
        image_url: ""
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
                item: this.emptyItem,
                isAdd: false
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
            item: result,
            isAdd: true
        })
        
        // this.props.history.push("/vehicles");
    }  

    render(){
        const {item} = this.state;            

        return <div>
            <AppNavbar/>
            <Container>
                <h2>Vehicle Roadtrip Calculator</h2>
                <img src={item.vehicle.image_url} alt="Vehicle" class="vehicle-img"/>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="manufacturer">Manufacturer</Label>
                        <Input type="text" name="manufacturer" id="manufacturer" value={item.vehicle.manufacturer || ""}
                            autoComplete="manufacturer" disabled="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="model">Model</Label>
                        <Input type="text" name="model" id="model" value={item.vehicle.model || ""}
                            autoComplete="model" disabled="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="year">Year</Label>
                        <Input type="value" name="year" id="year" value={item.vehicle.year || ""}
                            autoComplete="year" disabled="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Comsumption</Label>
                        <Input type="value" name="consumption" id="consumption" value={item.vehicle.consumption || ""}
                            autoComplete="consumption" disabled="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="distance">Distance</Label>
                        <Input type="value" name="distance" id="distance" value={item.distance || ""}
                            autoComplete="distance"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="fuelPrice">Fuel Price</Label>
                        <Input type="value" name="fuelPrice" id="fuelPrice" value={item.fuelPrice || ""}
                            autoComplete="fuelPrice"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="cost">Roadtrip Cost</Label>
                        <Input type="value" name="cost" id="cost" value={item.cost || ""}
                            autoComplete="cost" disabled="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Calculate</Button>{" "}
                        <Button color="secondary" tag={Link} to="/vehicles">Vehicles List</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(VehicleTripCost);