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

    emptyRoadtrip = {
        kilometers: 0,
        gas_price: 0
    };

    constructor(props){
        super(props);
        this.state = {
            item: this.emptyItem,
            isEdit: false,
            isAdd: false,
            roadtrip: this.emptyRoadtrip,
            roadtrip_cost: 0
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCalculatorChange = this.handleCalculatorChange.bind(this);
        this.calculateRoadTripCost = this.calculateRoadTripCost.bind(this);
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

    handleCalculatorChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let roadtrip = {...this.state.roadtrip};
        roadtrip[name] = value;
        this.setState({roadtrip});
    }

    async handleSubmit(event){
        console.log("Recebeu um submit")
        event.preventDefault();
        const {item} = this.state;

        //In this case it is an edit request
        if(!this.state.isAdd && !this.state.isEdit){
            this.setState({
                isEdit: true
            })
        }else{
            await fetch("/vehicles" + (item.id ? "/" + item.id : ""),{
                method: (item.id)? "PUT" : "POST",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(item),
            })
            
            this.props.history.push("/vehicles");

            if(this.state.isAdd)
                this.setState({isAdd: false});
            if(this.state.isEdit)
                this.setState({isEdit: false});
        }
            
    }  

    calculateRoadTripCost(){
        const {roadtrip} = this.state;
        const consumption = this.state.item.consumption;

        const result = ((consumption * roadtrip.kilometers) / 100) * roadtrip.gas_price;
        //Currently not rounding the value and not sure why
        const rounded_cost = Math.ceil(result * 100)/100;
        console.log(rounded_cost)
        this.setState({
            roadtrip_cost: rounded_cost
        })
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