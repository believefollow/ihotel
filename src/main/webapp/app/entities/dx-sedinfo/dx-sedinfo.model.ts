export interface IDxSedinfo {
  id?: number;
  yddx?: string | null;
  yddxmemo?: string | null;
  qxyddx?: string | null;
  qxydmemo?: string | null;
  czdx?: string | null;
  czmemo?: string | null;
  qxczdx?: string | null;
  qxczmemo?: string | null;
  yyedx?: string | null;
  yyememo?: string | null;
  fstime?: string | null;
  sffshm?: string | null;
  rzdx?: string | null;
  rzdxroomn?: string | null;
  jfdz?: string | null;
  blhy?: string | null;
  rzmemo?: string | null;
  blhymemo?: string | null;
  tfdx?: string | null;
  tfdxmemo?: string | null;
  fslb?: string | null;
  fslbmemo?: string | null;
}

export class DxSedinfo implements IDxSedinfo {
  constructor(
    public id?: number,
    public yddx?: string | null,
    public yddxmemo?: string | null,
    public qxyddx?: string | null,
    public qxydmemo?: string | null,
    public czdx?: string | null,
    public czmemo?: string | null,
    public qxczdx?: string | null,
    public qxczmemo?: string | null,
    public yyedx?: string | null,
    public yyememo?: string | null,
    public fstime?: string | null,
    public sffshm?: string | null,
    public rzdx?: string | null,
    public rzdxroomn?: string | null,
    public jfdz?: string | null,
    public blhy?: string | null,
    public rzmemo?: string | null,
    public blhymemo?: string | null,
    public tfdx?: string | null,
    public tfdxmemo?: string | null,
    public fslb?: string | null,
    public fslbmemo?: string | null
  ) {}
}

export function getDxSedinfoIdentifier(dxSedinfo: IDxSedinfo): number | undefined {
  return dxSedinfo.id;
}
