export interface IDKc {
  id?: number;
  depot?: string;
  spbm?: string;
  spmc?: string;
  xh?: string | null;
  dw?: string | null;
  price?: number | null;
  sl?: number | null;
  je?: number | null;
}

export class DKc implements IDKc {
  constructor(
    public id?: number,
    public depot?: string,
    public spbm?: string,
    public spmc?: string,
    public xh?: string | null,
    public dw?: string | null,
    public price?: number | null,
    public sl?: number | null,
    public je?: number | null
  ) {}
}

export function getDKcIdentifier(dKc: IDKc): number | undefined {
  return dKc.id;
}
