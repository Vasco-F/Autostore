import React, {Component} from "react";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AppNavbar from "./AppNavbar";
import {Link, withRouter} from "react-router-dom";

class VehicleEdit extends Component {
    
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
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount(){
        if(this.props.match.params.id !== "new"){
            const vehicle = await (await fetch(`/vehicles/${this.props.match.params.id}`)).json();
            this.setState({
                item: vehicle,
                isAdd: false
            })
        }else{
            this.setState({
                isAdd: true
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
        const {item} = this.state;

        await fetch("/vehicles/" + item.vehicleId,{
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item),
        })
        
        this.props.history.push("/vehicles");
            
    }  

    render(){
        const {item} = this.state;

        return <div>
            <AppNavbar/>
            <Container>
                <h2>Edit Vehicle</h2>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="manufacturer">Manufacturer</Label>
                        <Input type="text" name="manufacturer" id="manufacturer" value={item.manufacturer || ""}
                            onChange={this.handleChange} autoComplete="manufacturer"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="model">Model</Label>
                        <Input type="text" name="model" id="model" value={item.model || ""}
                            onChange={this.handleChange} autoComplete="model"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="year">Year</Label>
                        <Input type="value" name="year" id="year" value={item.year || ""}
                            onChange={this.handleChange} autoComplete="year"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Comsumption</Label>
                        <Input type="value" name="consumption" id="consumption" value={item.consumption || ""}
                            onChange={this.handleChange} autoComplete="consumption"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="image">Image URL</Label>
                        <Input type="value" name="image" id="image" value={item.image || ""}
                            onChange={this.handleChange} autoComplete="image"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{" "}
                        <Button color="secondary" tag={Link} to="/vehicles">Cancel</Button>
                    </FormGroup>
                </Form>                
            </Container>
        </div>
    }
}
export default withRouter(VehicleEdit);