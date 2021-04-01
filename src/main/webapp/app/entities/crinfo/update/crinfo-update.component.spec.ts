jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CrinfoService } from '../service/crinfo.service';
import { ICrinfo, Crinfo } from '../crinfo.model';

import { CrinfoUpdateComponent } from './crinfo-update.component';

describe('Component Tests', () => {
  describe('Crinfo Management Update Component', () => {
    let comp: CrinfoUpdateComponent;
    let fixture: ComponentFixture<CrinfoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let crinfoService: CrinfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CrinfoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CrinfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CrinfoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      crinfoService = TestBed.inject(CrinfoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const crinfo: ICrinfo = { id: 456 };

        activatedRoute.data = of({ crinfo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(crinfo));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const crinfo = { id: 123 };
        spyOn(crinfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ crinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: crinfo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(crinfoService.update).toHaveBeenCalledWith(crinfo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const crinfo = new Crinfo();
        spyOn(crinfoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ crinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: crinfo }));
        saveSubject.complete();

        // THEN
        expect(crinfoService.create).toHaveBeenCalledWith(crinfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const crinfo = { id: 123 };
        spyOn(crinfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ crinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(crinfoService.update).toHaveBeenCalledWith(crinfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
