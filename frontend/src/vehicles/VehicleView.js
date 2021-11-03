import React, {Component} from "react";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AppNavbar from "./AppNavbar";
import {Link, withRouter} from "react-router-dom";

import "./VehicleView.css";

class VehicleView extends Component {
    
    emptyItem = {
        manufacturer: "",
        model: "",
        year: 0,
        consumption: 0,
        image_url: ""
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

        return <div>
            <AppNavbar/>
            <Container>
                <h2>View Vehicle</h2>
                <img src={item.image} alt="Vehicle" class="vehicle-img"/>
                <Form>
                    <FormGroup>
                        <Label for="manufacturer">Manufacturer</Label>
                        <Input type="text" name="manufacturer" id="manufacturer" value={item.manufacturer || ""}
                            autoComplete="manufacturer" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="model">Model</Label>
                        <Input type="text" name="model" id="model" value={item.model || ""}
                            autoComplete="model" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="year">Year</Label>
                        <Input type="value" name="year" id="year" value={item.year || ""}
                            autoComplete="year" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Comsumption</Label>
                        <Input type="value" name="consumption" id="consumption" value={item.consumption || ""}
                            autoComplete="consumption" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="image">Image URL</Label>
                        <Input type="value" name="image" id="image" value={item.image || ""}
                            autoComplete="image" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" tag={Link} to={"/vehicles/" + item.vehicleId + "/update"}>Edit Vehicle</Button>
                        <Button color="secondary" tag={Link} to={"/vehicles/" + item.vehicleId + "/roadtrip-cost"}>Roadtrip Cost Calculator</Button>
                    </FormGroup>
                </Form> 
            </Container>
        </div>
    }
}
export default withRouter(VehicleView);