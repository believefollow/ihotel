export interface IDDepot {
  id?: number;
  depotid?: boolean;
  depot?: string;
}

export class DDepot implements IDDepot {
  constructor(public id?: number, public depotid?: boolean, public depot?: string) {
    this.depotid = this.depotid ?? false;
  }
}

export function getDDepotIdentifier(dDepot: IDDepot): number | undefined {
  return dDepot.id;
}
