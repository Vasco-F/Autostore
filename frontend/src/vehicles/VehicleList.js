import React, {Component} from "react";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from "./AppNavbar";
import {Link} from "react-router-dom";

class VehicleList extends Component {

    constructor(props){
        super(props);
        this.state = {
            vehicles: []
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount(){
        fetch("/vehicles")
            .then(response => response.json())
            .then(data => this.setState({vehicles: data}));
    }

    async remove(id) {
        await fetch(`/vehicles/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        }).then(() => {
            let updatedVehicles = [...this.state.vehicles].filter( i => i.id !== id);
            this.setState({vehicles: updatedVehicles});
        })
    }

    render(){
        const {vehicles, isLoading} = this.state;
        
        if(isLoading){
            return <p>Loading...</p>
        }

        const vehicleList = vehicles.map(vehicle => {
            return <tr key={vehicle.id}>
                <td style={{whiteSpace: "nowrap"}}>{vehicle.manufacturer}</td>
                <td>{vehicle.model}</td>
                <td>{vehicle.year}</td>
                <td>{vehicle.consumption}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/vehicles/" + vehicle.id}>View</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(vehicle.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <h3>Vehicles</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="35%">Manufacturer</th>
                                <th width="35%">Model</th>
                                <th width="15%">Year</th>
                                <th width="15%">Consumption</th>
                            </tr>
                        </thead>
                        <tbody>
                            {vehicleList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/vehicles/new">Add Vehicle</Button>
                    </div>
                </Container>
            </div>
        )
    }

}
export default VehicleList;

