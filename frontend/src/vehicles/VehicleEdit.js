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
        image: 0
    };

    constructor(props){
        super(props);
        this.state = {
            item: this.emptyItem,
            isEdit: false,
            isAdd: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        // this.handleEditClick = this.handleEditClick.bind(this);
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

    // handleEditClick(){
    //     //const currentValue = {...this.state.isEdit};
    //     this.setState({isEdit: true});

    //     console.log("Recebeu um handle edit")
    // }

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
                    "Accept": "application/json"
                    // "Content-Type": "application/json"
                },
                body: JSON.stringify(item),
            });
            this.props.history.push("/vehicles");

            if(this.state.isAdd)
                this.setState({isAdd: false});
            if(this.state.isEdit)
                this.setState({isEdit: false});
        }
            
    }  

    render(){
        // console.log("Deu render");
        const {item} = this.state;
        let title; //= <h2>{item.id ? "Edit Vehicle" : "Add Vehicle" }</h2>

        if(this.state.isEdit)
            title = <h2>Edit Vehicle</h2>;
        else if(this.state.isAdd)
            title = <h2>Add Vehicle</h2>;
        else
            title = <h2>View Vehicle</h2>;

        let button;
        if(this.state.isAdd || this.state.isEdit){
            button = <FormGroup>
                <Button color="primary" type="submit">Save</Button>{" "}
                <Button color="secondary" tag={Link} to="/vehicles">Cancel</Button>
            </FormGroup>;
        }else{
            button = <FormGroup>
                <Button color="primary">Edit</Button>
            </FormGroup>;
        }

        const disable_input = this.state.isEdit || this.state.isAdd;
        console.log("disable input= " + disable_input)

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="manufacturer">Manufacturer</Label>
                        <Input type="text" name="manufacturer" id="manufacturer" value={item.manufacturer || ""}
                            onChange={this.handleChange} autoComplete="manufacturer" disabled = {(disable_input) ? "" : "disabled"}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="model">Model</Label>
                        <Input type="text" name="model" id="model" value={item.model || ""}
                            onChange={this.handleChange} autoComplete="model" disabled = {(disable_input) ? "" : "disabled"}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="year">Year</Label>
                        <Input type="value" name="year" id="year" value={item.year || ""}
                            onChange={this.handleChange} autoComplete="year" disabled = {(disable_input) ? "" : "disabled"}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Comsumption</Label>
                        <Input type="value" name="consumption" id="consumption" value={item.consumption || ""}
                            onChange={this.handleChange} autoComplete="consumption" disabled = {(disable_input) ? "" : "disabled"}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="image">Image</Label>
                        <Input type="file" name="image" id="image" 
                        onChange={this.handleChange} disabled = {(disable_input) ? "" : "disabled"}/>
                    </FormGroup>
                    {button}
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(VehicleEdit);