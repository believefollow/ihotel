export interface IComset {
  id?: number;
  comNum?: string;
  comBytes?: string;
  comDatabit?: string;
  comParitycheck?: string;
  comStopbit?: string;
  comFunction?: number;
}

export class Comset implements IComset {
  constructor(
    public id?: number,
    public comNum?: string,
    public comBytes?: string,
    public comDatabit?: string,
    public comParitycheck?: string,
    public comStopbit?: string,
    public comFunction?: number
  ) {}
}

export function getComsetIdentifier(comset: IComset): number | undefined {
  return comset.id;
}
