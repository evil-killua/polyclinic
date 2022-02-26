export class User{
    id!:number;
	userName!: string;
	userPwd!: string;
    firstName!:string;
    lastName!:string;
    birthday!:string;
    address!:string;
    medicalCardNumber!:number;
    email!:string;
    phone!:string;
	roles?: string[];
}