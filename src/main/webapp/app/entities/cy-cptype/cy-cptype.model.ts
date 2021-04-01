export interface ICyCptype {
  id?: number;
  cptdm?: string;
  cptname?: string;
  printsign?: boolean;
  printer?: string | null;
  printnum?: number | null;
  printcut?: number | null;
  syssign?: boolean | null;
  typesign?: string | null;
  qy?: string | null;
}

export class CyCptype implements ICyCptype {
  constructor(
    public id?: number,
    public cptdm?: string,
    public cptname?: string,
    public printsign?: boolean,
    public printer?: string | null,
    public printnum?: number | null,
    public printcut?: number | null,
    public syssign?: boolean | null,
    public typesign?: string | null,
    public qy?: string | null
  ) {
    this.printsign = this.printsign ?? false;
    this.syssign = this.syssign ?? false;
  }
}

export function getCyCptypeIdentifier(cyCptype: ICyCptype): number | undefined {
  return cyCptype.id;
}
