export interface IDCompany {
  company?: string;
  linkman?: string | null;
  phone?: string | null;
  address?: string | null;
  remark?: string | null;
  fax?: string | null;
  id?: number;
}

export class DCompany implements IDCompany {
  constructor(
    public company?: string,
    public linkman?: string | null,
    public phone?: string | null,
    public address?: string | null,
    public remark?: string | null,
    public fax?: string | null,
    public id?: number
  ) {}
}

export function getDCompanyIdentifier(dCompany: IDCompany): number | undefined {
  return dCompany.id;
}
