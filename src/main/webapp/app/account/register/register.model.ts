export class Registration {
  constructor(
    public login: string,
    public email: string,
    public password: string,
    public langKey: string,
    public lastName: string,
    public firstName: string,
    public contact: string,
    public ville: string,
    public communeOuQuartier: string,
    public authorities: string[]
  ) {}
}
