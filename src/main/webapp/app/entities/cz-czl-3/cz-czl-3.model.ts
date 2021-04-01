export interface ICzCzl3 {
  id?: number;
  zfs?: number | null;
  kfs?: number | null;
  protocoln?: string | null;
  roomtype?: string | null;
  sl?: number | null;
}

export class CzCzl3 implements ICzCzl3 {
  constructor(
    public id?: number,
    public zfs?: number | null,
    public kfs?: number | null,
    public protocoln?: string | null,
    public roomtype?: string | null,
    public sl?: number | null
  ) {}
}

export function getCzCzl3Identifier(czCzl3: ICzCzl3): number | undefined {
  return czCzl3.id;
}
