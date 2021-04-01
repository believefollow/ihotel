import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AuditinfoDetailComponent } from './auditinfo-detail.component';

describe('Component Tests', () => {
  describe('Auditinfo Management Detail Component', () => {
    let comp: AuditinfoDetailComponent;
    let fixture: ComponentFixture<AuditinfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AuditinfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ auditinfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AuditinfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditinfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auditinfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditinfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
