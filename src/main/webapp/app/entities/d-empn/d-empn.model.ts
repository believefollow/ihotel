export interface IDEmpn {
  id?: number;
  empnid?: number;
  empn?: string;
  deptid?: number | null;
  phone?: string | null;
}

export class DEmpn implements IDEmpn {
  constructor(
    public id?: number,
    public empnid?: number,
    public empn?: string,
    public deptid?: number | null,
    public phone?: string | null
  ) {}
}

export function getDEmpnIdentifier(dEmpn: IDEmpn): number | undefined {
  return dEmpn.id;
}
